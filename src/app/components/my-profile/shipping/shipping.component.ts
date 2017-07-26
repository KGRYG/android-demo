import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserShipping} from "../../../models/user-shipping";
import {ShippingService} from "../../../services/shipping.service";
import {User} from "../../../models/user";
import {UserDataService} from "../../../services/user-data.service";
import {Subscription} from "rxjs/Subscription";
import * as myGlobals from '../../../globals';

@Component({
  selector: 'app-shipping',
  templateUrl: './shipping.component.html',
  styleUrls: ['./shipping.component.css']
})
export class ShippingComponent implements OnInit, OnDestroy {

  userShipping: UserShipping = new UserShipping();
  userShippingList: UserShipping[] = [];
  defaultUserShipping = new UserShipping();
  defaultShippingSet = false;
  user = new User();
  subscription: Subscription;
  selectedShippingTab = 0;
  stateList: string[] = [];

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
    for (let s in myGlobals.US_STATES) {
      this.stateList.push(s);
    }

    this.userShippingList = this.user.userShippingList;
    if (this.userShippingList.length > 0) {
      this.defaultUserShipping = this.user.userShippingList.find(item => item.userShippingDefault === true);
    }
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  selectedShippingChange(val: number) {
    this.selectedShippingTab = val;
  }

  onNewShipping() {
    this.shippingService.newShipping(this.userShipping).subscribe(
      res => {

        const shipping = this.user.userShippingList.find(item => item.id === this.userShipping.id);
        if (!shipping) {
          this.user.userShippingList.push(this.userShipping);
          this.userData.setUser(this.user);
        } else {
          this.user.userShippingList.splice(this.user.userShippingList.indexOf(shipping),1);
          this.user.userShippingList.push(this.userShipping);
          this.userData.setUser(this.user);
        }
        this.userShipping = new UserShipping();
        this.selectedShippingTab = 0;
      },
      error => {
        console.log(error.text());
      }
    );
  }

  onUpdateShipping(shipping: UserShipping) {
    this.userShipping = shipping;
    this.selectedShippingTab = 1;
  }

  onRemoveShipping(shipping: UserShipping) {
    this.shippingService.removeShipping(shipping.id).subscribe(
      res => {
        const index = this.userShippingList.indexOf(shipping);
        this.userShippingList.splice(index, 1);
        this.user.userShippingList = this.userShippingList;
        this.userData.setUser(this.user);
      },
      error => {
        console.log(error.text());
      }
    );
  }

  setDefaultShipping(shipping: UserShipping) {
    this.shippingService.setDefaultShipping(shipping.id).subscribe(
      res => {
        this.user.userShippingList.filter(item => item.id === shipping.id).map(item => item.userShippingDefault = true);
        this.userData.setUser(this.user);
        this.defaultUserShipping = shipping;
        this.defaultShippingSet = true;
      },
      error => {
        console.log(error.text());
      }
    );
  }
}
