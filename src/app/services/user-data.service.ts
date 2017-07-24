import { Injectable } from '@angular/core';
import {User} from "../models/user";
import {Subject} from "rxjs/Subject";

@Injectable()
export class UserDataService {
  private currentUser = new User();
  userChanged = new Subject<User>();

  constructor() { }

  getUser() {
    return Object.assign({}, this.currentUser);
  }

  setUser(user: User) {
    this.currentUser = user;
    this.userChanged.next(Object.assign({}, this.currentUser));
  }

}
