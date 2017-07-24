import {Component, OnDestroy, OnInit} from '@angular/core';
import {PaymentService} from "../../../services/payment.service";
import {UserPayment} from "../../../models/user-payment";
import {UserBilling} from "../../../models/user-billing";
import {User} from "../../../models/user";
import {UserDataService} from "../../../services/user-data.service";
import {Subscription} from "rxjs/Subscription";

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit, OnDestroy {

  private defaultPaymentSet = false;
  private defaultUserPaymentId: number;
  private userPaymentList: UserPayment[] = [];
  private userPayment: UserPayment = new UserPayment();
  private userBilling: UserBilling = new UserBilling();
  user = new User();
  subscription: Subscription;

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
    if(this.userPaymentList){
      this.defaultUserPaymentId = this.user.userPaymentList.find(item => item.defaultPayment === true).id;
    }
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  setDefaultPayment() {
    this.defaultPaymentSet = false;
    this.paymentService.setDefaultPayment(this.defaultUserPaymentId).subscribe(
      res => {
        this.defaultPaymentSet = true;
      },
      error => {
        console.log(error.text());
      }
    );
  }

  onNewPayment() {
    this.paymentService.newPayment(this.userPayment).subscribe(
      res => {
        this.userPayment = new UserPayment();
      },
      error => {
        console.log(error.text());
      }
    );
  }

  onUpdatePayment (payment: UserPayment) {
    this.userPayment = payment;
    this.userBilling = payment.userBilling;
  }

  onRemovePayment(id:number) {
    this.paymentService.removePayment(id).subscribe(
      res => {
      },
      error => {
        console.log(error.text());
      }
    );
  }

}
