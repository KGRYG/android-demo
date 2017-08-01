import { Injectable } from '@angular/core';
import {Http, Headers} from '@angular/http';
import {ShippingAddress} from '../models/shipping-address';
import {BillingAddress} from '../models/billing-address';
import {Payment} from '../models/payment';
import * as myGlobals from '../globals';


@Injectable()
export class CheckoutService {

  constructor(private http: Http) { }

  checkout(
  	shippingAddress: ShippingAddress,
  	billingAddress: BillingAddress,
  	payment:Payment,
  	shippingMethod:string
  	){
		let url = myGlobals.BASE_API_URL + "/checkout";
		let order ={
			"shippingAddress" : shippingAddress,
			"billingAddress" : billingAddress,
			"payment" : payment,
			"shippingMethod" : shippingMethod
		}

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.post(url, order, {headers: tokenHeader, withCredentials: true});
  }

  getUserOrder() {
  	let url = myGlobals.BASE_API_URL + "/checkout/getUserOrder";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.get(url, {headers: tokenHeader, withCredentials: true});

  }

}
