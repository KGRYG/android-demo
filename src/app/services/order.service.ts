import { Injectable } from '@angular/core';
import {Http, Headers} from '@angular/http';
import * as myGlobals from '../globals';

@Injectable()
export class OrderService {

  constructor(private http:Http) { }

  getOrderList() {
  	let url = myGlobals.BASE_API_URL + "/order/getOrderList";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.get(url, {headers: tokenHeader});
  }
}
