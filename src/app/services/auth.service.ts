import { Injectable } from '@angular/core';
import {Http, Headers} from '@angular/http';
import * as myGlobals from '../globals';

@Injectable()
export class AuthService {

  constructor(private http: Http) { }

  sendCredentials(username: string, password: string) {
    let url = myGlobals.BASE_API_URL + '/token';
    let encodedCredentials = btoa(username + ':' + password);
    let basicHeader = 'Basic ' + encodedCredentials;
    let headers = new Headers ({
      'Content-Type' : 'application/x-www-form-urlencoded',
      'Authorization' : basicHeader,
      'X-Requested-With': 'XMLHttpRequest'
    });
    return this.http.get(url, {headers: headers, withCredentials: true});
  }

  checkSession() {
    let url = myGlobals.BASE_API_URL + '/checkSession';
    let headers = new Headers ({
      'x-auth-token' : localStorage.getItem('xAuthToken')
    });
    return this.http.get(url, {headers: headers, withCredentials: true});
  }

  isAuthenticated() {
    return localStorage.getItem('xAuthToken') != null;
  }

  logout() {
    let url = myGlobals.BASE_API_URL + '/user/logout';
    let headers = new Headers ({
      'x-auth-token' : localStorage.getItem('xAuthToken')
    });

    return this.http.post(url, '', {headers: headers, withCredentials: true});
  }
}
