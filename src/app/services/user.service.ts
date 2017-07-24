import { Injectable } from '@angular/core';
import {Http, Headers} from '@angular/http';
import {User} from '../models/user';
import * as myGlobals from '../globals';
import 'rxjs/Rx';

@Injectable()
export class UserService {

  constructor(private http:Http) { }

  newUser(username: string, email:string) {
  	let url = myGlobals.BASE_API_URL + '/user/newUser';
  	let userInfo = {
  		"username" : username,
  		"email" : email
  	}
  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem('xAuthToken')
  	});

  	return this.http.post(url, JSON.stringify(userInfo), {headers : tokenHeader, withCredentials: true});
  }

  updateUserInfo(user: User, newPassword: string, currentPassword: string) {
    let url = myGlobals.BASE_API_URL + "/user/updateUserInfo";
    let userInfo = {
      "id" : user.id,
      "firstName" : user.firstName,
      "lastName" : user.lastName,
      "username" : user.username,
      "currentPassword" : currentPassword,
      "email" : user.email,
      "newPassword" :newPassword
    };

    let tokenHeader = new Headers({
      'Content-Type' : 'application/json',
      'x-auth-token' : localStorage.getItem("xAuthToken")
    });
    return this.http.post(url, JSON.stringify(userInfo), {headers:tokenHeader, withCredentials: true});
  }

  retrievePassword(email:string) {
  	let url = myGlobals.BASE_API_URL +'/user/forgetPassword';
  	let userInfo = {
  		"email" : email
  	}
  	let tokenHeader = new Headers({
  		'Content-Type' : 'application/json',
  		'x-auth-token' : localStorage.getItem('xAuthToken')
  	});

  	return this.http.post(url, JSON.stringify(userInfo), {headers : tokenHeader, withCredentials: true});
  }

  getCurrentUser() {
    let url = myGlobals.BASE_API_URL +'/user/getCurrentUser';

    let tokenHeader = new Headers({
      'Content-Type' : 'application/json',
      'x-auth-token' : localStorage.getItem('xAuthToken')
    });

    return this.http.get(url, {headers : tokenHeader, withCredentials: true});
  }

}
