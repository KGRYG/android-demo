import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { UserShipping } from '../models/user-shipping';
import * as myGlobals from '../globals';

@Injectable()
export class ShippingService {

  constructor(private http : Http) { }

  newShipping(shipping: UserShipping) {
  	let url = myGlobals.BASE_API_URL + "/shipping/add";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.post(url, JSON.stringify(shipping), {headers: tokenHeader, withCredentials: true});
  }

  getUserShippingList() {
  	let url = myGlobals.BASE_API_URL + "/shipping/getUserShippingList";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.get(url, {headers: tokenHeader, withCredentials: true});
  }

  removeShipping(id:number){
  	let url = myGlobals.BASE_API_URL + "/shipping/remove";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.post(url, id, {headers: tokenHeader, withCredentials: true});
  }

  setDefaultShipping(id:number){
  	let url = myGlobals.BASE_API_URL + "/shipping/setDefault";

  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem("xAuthToken")
  	});
  	return this.http.post(url, id, {headers: tokenHeader, withCredentials: true});
  }
}
