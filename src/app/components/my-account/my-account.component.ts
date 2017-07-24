import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-my-account',
  templateUrl: './my-account.component.html',
  styleUrls: ['./my-account.component.css']
})
export class MyAccountComponent implements OnInit {
  routeLinks:any[];
  activeLinkIndex: number;

  constructor(private router: Router) {
    this.routeLinks = [
      {label: 'Login', link: 'login'},
      {label: 'New Account', link: 'new-account'},
      {label: 'Forget Password', link: 'forget-password'}
    ];

  }

  ngOnInit() {
    switch(this.router.url) {
      case '/myaccount/new-account': {
        this.activeLinkIndex = 1;
        break;
      }
      case '/myaccount/forget-password': {
        this.activeLinkIndex = 2;
        break;
      }
      default: {
        this.activeLinkIndex = 0;
        break;
      }
    }
  }

}
