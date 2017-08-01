import { Injectable } from '@angular/core';
import {Http, Headers} from '@angular/http';
import * as myGlobals from '../globals';

@Injectable()
export class BookService {

  constructor(private http:Http) { }

  getBookList() {
  	let url = myGlobals.BASE_API_URL + "/book/bookList";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.get(url, {headers: tokenHeader, withCredentials: true});
  }

  getBook(id: number) {
  	let url = myGlobals.BASE_API_URL + "/book/"+id;

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.get(url, {headers: tokenHeader, withCredentials: true});
  }

  searchBook(keyword: string) {
  	let url = myGlobals.BASE_API_URL + "/book/searchBook";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.post(url, keyword, {headers: tokenHeader, withCredentials: true});
  }

}
