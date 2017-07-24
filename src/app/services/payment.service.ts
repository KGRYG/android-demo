import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { UserPayment } from '../models/user-payment';
import * as myGlobals from '../globals';

@Injectable()
export class PaymentService {

  constructor(private http : Http) { }

  newPayment(payment: UserPayment) {
  	let url = myGlobals.BASE_API_URL + "/payment/add";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.post(url, JSON.stringify(payment), {headers: tokenHeader, withCredentials: true});
  }

  getUserPaymentList() {
  	let url = myGlobals.BASE_API_URL + "/payment/getUserPaymentList";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.get(url,  {headers: tokenHeader, withCredentials: true});
  }

  removePayment(id: number) {
  	let url = myGlobals.BASE_API_URL + "/payment/remove";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.post(url, id, {headers: tokenHeader, withCredentials: true});
  }

  setDefaultPayment (id: number) {
  	let url = myGlobals.BASE_API_URL + "/payment/setDefault";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.post(url, id, {headers: tokenHeader, withCredentials: true});
  }
}
