import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserShipping} from "../../../models/user-shipping";
import {ShippingService} from "../../../services/shipping.service";
import {User} from "../../../models/user";
import {UserService} from "../../../services/user.service";
import {UserDataService} from "../../../services/user-data.service";
import {Subscription} from "rxjs/Subscription";

@Component({
  selector: 'app-shipping',
  templateUrl: './shipping.component.html',
  styleUrls: ['./shipping.component.css']
})
export class ShippingComponent implements OnInit, OnDestroy {

  userShipping: UserShipping = new UserShipping();
  userShippingList: UserShipping[] = [];
  defaultUserShippingId: number;
  defaultShippingSet = false;
  user = new User();
  subscription: Subscription;

  constructor(
    private shippingService: ShippingService,
    private userData: UserDataService) { }

  ngOnInit() {
    this.subscription = this.userData.userChanged
      .subscribe(
        (user) => {
          this.user = user;
        }
      );
    this.user = this.userData.getUser();

    this.userShippingList = this.user.userShippingList;
    if (this.userShippingList) {
      this.defaultUserShippingId = this.user.userShippingList.find(item => item.userShippingDefault === true).id;
    }
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  onNewShipping() {
    this.shippingService.newShipping(this.userShipping).subscribe(
      res => {
        this.userShipping = new UserShipping();
      },
      error => {
        console.log(error.text());
      }
    );
  }

  onUpdateShipping(shipping: UserShipping) {
    this.userShipping = shipping;
  }

  onRemoveShipping(id: number) {
    this.shippingService.removeShipping(id).subscribe(
      res => {
      },
      error => {
        console.log(error.text());
      }
    );
  }

  setDefaultShipping() {
    this.shippingService.setDefaultShipping(this.defaultUserShippingId).subscribe(
      res => {
        this.defaultShippingSet = true;
      },
      error => {
        console.log(error.text());
      }
    );
  }
}
