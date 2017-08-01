import {Component, OnDestroy, OnInit} from '@angular/core';
import {Book} from "../../models/book";
import {ActivatedRoute, Params} from "@angular/router";
import {CartService} from "../../services/cart.service";
import {BookDataService} from "../../services/book-data.service";
import {Subscription} from "rxjs/Subscription";
import {BookService} from "../../services/book.service";

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit, OnDestroy {

  bookId: number;
  book = new Book();
  qty = 1;
  addBookSuccess = false;
  notEnoughStock = false;
  subscription: Subscription;
  numberList = [1,2,3,4,5,6,7,8,9,10];

  constructor(
    private cartService: CartService,
    private route: ActivatedRoute,
    private bookDataService: BookDataService,
    private bookService: BookService
  ) { }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  ngOnInit() {

    this.route.params
      .subscribe(
        (params: Params) => {
          this.bookId = +params['id'];
        }
      );
    this.subscription = this.bookDataService.bookChanged
      .subscribe(
        (books: Book[]) => {
          this.book = books.find(item => item.id === this.bookId);
        }
      );

    if (this.bookDataService.getBookList().length < 1) {
      this.bookService.getBookList().subscribe(
        res => {
          this.bookDataService.setBookList(res.json());
          this.book = this.bookDataService.getBook(this.bookId);
        },
        error => {
          console.log(error);
        }
      );
    } else {
      this.book = this.bookDataService.getBook(this.bookId);
    }
  }

  onAddToCart() {
    this.cartService.addItem(this.bookId, this.qty).subscribe(
      res => {
        this.addBookSuccess = true;
      },
      err => {
        this.notEnoughStock = true;
      }
    );
  }

}
