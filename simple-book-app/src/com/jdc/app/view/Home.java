package com.jdc.app.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.jdc.app.entity.Book;
import com.jdc.app.entity.Category;
import com.jdc.app.entity.Sale;
import com.jdc.app.entity.SaleDTO;
import com.jdc.app.entity.SaleDetail;
import com.jdc.app.service.BookService;
import com.jdc.app.service.CategoryService;
import com.jdc.app.service.SaleService;
import com.jdc.app.util.AppException;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Home {

	@FXML
	private ComboBox<Category> category;
	@FXML
	private TextField bookName;
	@FXML
	private TableView<Book> tblList;
	@FXML
	private Label headerTotal;
	@FXML
	private TableView<SaleDetail> cartList;
	@FXML
	private Label subTotal;
	@FXML
	private Label tax;
	@FXML
	private Label total;
	
	private SaleDTO saleDTO;
	private List<SaleDetail> detailList;
	private SaleDetail sd;
	private CategoryService catService;
	private BookService bookService;
	private SaleService saleService;
	
	public void search() {
		tblList.getItems().clear();
		List<Book> bookList = bookService.findByParams(category.getValue(), null, bookName.getText(), null);
		tblList.getItems().addAll(bookList);
	}
	
	public void clear() {
		detailList.forEach(sd -> {
			if(sd.getId() > 0) {
				sd.setDelete(true);
				detailList.add(sd);
			}
		});
		cartList.getItems().clear();
		detailList.clear();
		calculate();
	}
	
	public void paid() {
		try {
			if(null == cartList.getItems()) {
				throw new AppException("Please add book to cart!");
			}
			if(null == saleDTO) {
				saleDTO = new SaleDTO();
				Sale s = saleDTO.getSale();
				s.setSaleDate(LocalDate.now());
				s.setSaleTime(LocalTime.now());
				s.setSubTotal(Integer.parseInt(subTotal.getText()));
				s.setTax(Integer.parseInt(tax.getText()));
			}
			
			saleDTO.getDetail().clear();
			saleDTO.getDetail().addAll(cartList.getItems());
			
			saleService.save(saleDTO);
			
			prepare();
			clear();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addToCart(MouseEvent event) {
		if(event.getClickCount() == 2) {
			// get select items
			Book book = tblList.getSelectionModel().getSelectedItem();

			//find sale detail in cart
			sd = cartList.getItems().stream().filter(sd -> sd.getBook().getId() == book.getId())
											 .findFirst().orElse(null);
			
			// if null create sale detail instance
			if(null == sd) {
				sd = new SaleDetail();			
				sd.setQuantity(1);
				sd.setBook(book);
				sd.setUnitPrice(book.getPrice());
				detailList.add(sd);
	
			} else {
				sd.setQuantity(sd.getQuantity() + 1);
				detailList = new ArrayList<>(cartList.getItems());
	
			}
			
			prepare();
			calculate();
		}
	}
	
	private void prepare() {
		cartList.getItems().clear();
		cartList.getItems().addAll(detailList);
		sd = null;
	}
	
	private void calculate() {
		int sbTtl = 0;
		List<Integer> prices = cartList.getItems().stream().map(a -> a.getUnitPrice() * a.getQuantity()).collect(Collectors.toList());
		for(int i : prices) {
			sbTtl += i;
		}
		subTotal.setText(String.valueOf(sbTtl));
		int tx = (int)(sbTtl * 0.05);
		tax.setText(String.valueOf(tx));
		total.setText(String.valueOf(sbTtl + tx));
	}
	
	@FXML
	private void initialize() {
		catService = CategoryService.getInstance();
		bookService = BookService.getInstance();
		saleService = SaleService.getInstance();
		detailList = new ArrayList<>();
		category.getItems().addAll(catService.findAll());
		search();
		calculate();
		headerTotal.textProperty().bind(total.textProperty());
		
	}
	
}