import {Component, OnDestroy, OnInit} from '@angular/core';
import {PaymentService} from "../../../services/payment.service";
import {UserPayment} from "../../../models/user-payment";
import {UserBilling} from "../../../models/user-billing";
import {User} from "../../../models/user";
import {UserDataService} from "../../../services/user-data.service";
import {Subscription} from "rxjs/Subscription";
import * as myGlobals from '../../../globals';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit, OnDestroy {

  defaultPaymentSet = false;
  defaultUserPayment: UserPayment = new UserPayment();
  userPaymentList: UserPayment[] = [];
  userPayment: UserPayment = new UserPayment();
  userBilling: UserBilling = new UserBilling();
  stateList: string[] = [];
  user = new User();
  subscription: Subscription;
  selectedBillingTab = 0;

  constructor(private paymentService: PaymentService,
              private userData: UserDataService) { }

  ngOnInit() {
    this.subscription = this.userData.userChanged
      .subscribe(
        (user) => {
          this.user = user;
        }
      );
    this.user = this.userData.getUser();

    this.userPaymentList = this.user.userPaymentList;
    if(this.userPaymentList.length > 0){
      this.defaultUserPayment = this.user.userPaymentList.find(item => item.defaultPayment === true);
    }

    for (let s in myGlobals.US_STATES) {
      this.stateList.push(s);
    }
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  setDefaultPayment(userPayment: UserPayment) {
    this.paymentService.setDefaultPayment(userPayment.id).subscribe(
      res => {
        this.user.userPaymentList.filter(item => item.id === userPayment.id).map(item => item.defaultPayment = true);
        this.userData.setUser(this.user);
        this.defaultUserPayment = userPayment;
        this.defaultPaymentSet = true;
      },
      error => {
        console.log(error.text());
      }
    );
  }

  onNewPayment() {
    this.userPayment.userBilling = this.userBilling;
    this.paymentService.newPayment(this.userPayment).subscribe(
      res => {
        const payment = this.user.userPaymentList.find(item => item.id === this.userPayment.id);
        if (!payment) {
          this.user.userPaymentList.push(this.userPayment);
          this.userData.setUser(this.user);
        } else {
          this.user.userPaymentList.splice(this.user.userPaymentList.indexOf(payment),1);
          this.user.userPaymentList.push(this.userPayment);
          this.userData.setUser(this.user);
        }
        this.selectedBillingTab = 0;
        this.userPayment = new UserPayment();
        this.userBilling = new UserBilling();
      },
      error => {
        console.log(error.text());
      }
    );
  }

  onUpdatePayment (payment: UserPayment) {
    this.userPayment = payment;
    this.userBilling = payment.userBilling;
    this.selectedBillingTab = 1;
  }

  onRemovePayment(payment: UserPayment) {
    this.paymentService.removePayment(payment.id).subscribe(
      res => {
        const index = this.userPaymentList.indexOf(payment);
        this.userPaymentList.splice(index, 1);
        this.user.userPaymentList = this.userPaymentList;
        this.userData.setUser(this.user);
      },
      error => {
        console.log(error.text());
      }
    );
  }

  selectedBillingChange(val: number) {
    this.selectedBillingTab = val;
  }

}
