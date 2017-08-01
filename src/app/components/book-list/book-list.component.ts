import {Component, OnInit} from '@angular/core';
import {Book} from "../../models/book";
import {Router} from "@angular/router";
import {BookService} from "../../services/book.service";
import {BookDataService} from "../../services/book-data.service";

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {


  filterQuery = "";
  rowsOnPage = 5;
  selectedBook: Book;
  bookList: Book[];


  constructor(
    private bookService: BookService,
    private router: Router,
    private bookDataService: BookDataService
  ) { }



  ngOnInit() {

    if (this.bookDataService.getBookList().length < 1) {
      this.bookService.getBookList().subscribe(
        res => {
          this.bookDataService.setBookList(res.json());
          this.bookList = this.bookDataService.getBookList();
        },
        error => {
          console.log(error);
        }
      );
    } else {
      this.bookList = this.bookDataService.getBookList();
    }
  }

  onSelect(book: Book) {
    this.selectedBook = book;
    this.router.navigate(['/bookdetail', this.selectedBook.id]);
  }

}
