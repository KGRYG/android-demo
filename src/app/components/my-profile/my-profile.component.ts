import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import 'rxjs/Rx';
import {UserDataService} from "../../services/user-data.service";

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {

  routeLinks:any[];
  activeLinkIndex: number;
  dataFetched = false;

  constructor(
    private router: Router,
    private userService: UserService,
    private userData: UserDataService
  ) {
    this.routeLinks = [
      {label: 'Edit', link: 'edit'},
      {label: 'Orders', link: 'orders'},
      {label: 'Payment', link: 'payment'},
      {label: 'Shipping', link: 'shipping'}
    ];
  }

  ngOnInit() {
    this.userService.getCurrentUser()
      .map(
        (res) => {
          return res.json();
        }
      )
      .subscribe(
        (user) => {
          this.dataFetched = true;
          this.userData.setUser(user);
        }
      );

    switch(this.router.url) {
      case '/myprofile/orders': {
        this.activeLinkIndex = 1;
        break;
      }
      case '/myprofile/payment': {
        this.activeLinkIndex = 2;
        break;
      }
      case '/myprofile/shipping': {
        this.activeLinkIndex = 3;
        break;
      }
      default: {
        this.activeLinkIndex = 0;
        break;
      }
    }
  }
}
