import { Injectable } from '@angular/core';
import {Subject} from "rxjs/Subject";
import {Book} from "../models/book";

@Injectable()
export class BookDataService {

  private bookList: Book[] = [];
  bookChanged = new Subject<Book[]>();

  constructor() { }

  getBookList() {
    return this.bookList.slice();
  }

  setBookList(books: Book[]) {
    this.bookList = books;
    this.bookChanged.next(this.bookList.slice());
  }

  getBook(id: number) {
    return this.bookList.find(item => item.id === id);
  }

}
