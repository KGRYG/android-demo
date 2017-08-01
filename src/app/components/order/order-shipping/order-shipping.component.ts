import {Component, OnDestroy, OnInit} from '@angular/core';
import {UserShipping} from "../../../models/user-shipping";
import {ShippingAddress} from "../../../models/shipping-address";
import {Router} from "@angular/router";
import * as myGlobals from '../../../globals';
import {OrderDataService} from "../../../services/order-data.service";
import {ShippingService} from "../../../services/shipping.service";
import {Order} from "../../../models/order";
import {Subscription} from "rxjs/Subscription";

@Component({
  selector: 'app-order-shipping',
  templateUrl: './order-shipping.component.html',
  styleUrls: ['./order-shipping.component.css']
})
export class OrderShippingComponent implements OnInit, OnDestroy {

  shippingAddress: ShippingAddress = new ShippingAddress();
  stateList = [];
  userShippingList: UserShipping[] = [];
  emptyShippingList = true;
  subscription: Subscription;
  order = new Order();

  constructor(
    private router: Router,
    private orderData: OrderDataService,
    private shippingService: ShippingService
  ) {
  }

  ngOnInit() {
    this.subscription = this.orderData.orderChanged
      .subscribe(
        (order) => {
          this.order = order;
        }
      );
    this.order = this.orderData.getOrder();
    this.getShippingList();
    for (let s in myGlobals.US_STATES) {
      this.stateList.push(s);
    }
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  setShippingAddress(userShipping: UserShipping) {
    this.shippingAddress.shippingAddressName = userShipping.userShippingName;
    this.shippingAddress.shippingAddressStreet1 = userShipping.userShippingStreet1;
    this.shippingAddress.shippingAddressStreet2 = userShipping.userShippingStreet2;
    this.shippingAddress.shippingAddressCity = userShipping.userShippingCity;
    this.shippingAddress.shippingAddressState = userShipping.userShippingState;
    this.shippingAddress.shippingAddressCountry = userShipping.userShippingCountry;
    this.shippingAddress.shippingAddressZipcode = userShipping.userShippingZipcode;
    this.order.shippingAddress = this.shippingAddress;
    this.orderData.setOrder(this.order);
  }

  goToPayment() {
    this.router.navigate(['/order/payment']);
  }

  getShippingList() {
    this.shippingService.getUserShippingList().subscribe(
      res=>{
        this.userShippingList = res.json();

        if(this.userShippingList.length) {
          this.emptyShippingList = false;

          for (let userShipping of this.userShippingList) {
            if(userShipping.userShippingDefault) {
              this.setShippingAddress(userShipping);
              return;
            }
          }
        }
      },
      error=>{
        console.log(error.text());
      }
    );
  }

}
