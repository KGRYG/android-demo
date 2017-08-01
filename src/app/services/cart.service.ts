import { Injectable } from '@angular/core';
import {Http, Headers} from '@angular/http';
import * as myGlobals from '../globals';

@Injectable()
export class CartService {

  constructor(private http:Http) { }

  addItem(id:number, qty: number) {
  	let url = myGlobals.BASE_API_URL + "/cart/add";
  	let cartItemInfo = {
  		"bookId" : id,
  		"qty" : qty
  	}
  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.post(url, cartItemInfo, {headers: tokenHeader, withCredentials: true});
  }

  getCartItemList() {
  	let url = myGlobals.BASE_API_URL + "/cart/getCartItemList";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.get(url, {headers: tokenHeader, withCredentials: true});
  }

  getShoppingCart() {
  	let url = myGlobals.BASE_API_URL + "/cart/getShoppingCart";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.get(url, {headers: tokenHeader, withCredentials: true});
  }

  updateCartItem(cartItemId: number, qty: number) {
  	let url = myGlobals.BASE_API_URL + "/cart/updateCartItem";
  	let cartItemInfo = {
  		"cartItemId" : cartItemId,
  		"qty" : qty
  	}
  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.post(url, cartItemInfo, {headers: tokenHeader, withCredentials: true});
  }

  removeCartItem(id: number) {
  	let url = myGlobals.BASE_API_URL + "/cart/removeItem";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.post(url, id, {headers: tokenHeader, withCredentials: true});
  }

}
