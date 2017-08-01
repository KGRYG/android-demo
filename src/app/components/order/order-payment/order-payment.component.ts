import {Component, OnDestroy, OnInit} from '@angular/core';
import {BillingAddress} from "../../../models/billing-address";
import {ShippingAddress} from "../../../models/shipping-address";
import {PaymentService} from "../../../services/payment.service";
import {UserPayment} from "../../../models/user-payment";
import {Payment} from "../../../models/payment";
import {Order} from "../../../models/order";
import {Subscription} from "rxjs/Subscription";
import {OrderDataService} from "../../../services/order-data.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-order-payment',
  templateUrl: './order-payment.component.html',
  styleUrls: ['./order-payment.component.css']
})
export class OrderPaymentComponent implements OnInit, OnDestroy {

  billingAddress = new BillingAddress();
  shippingAddress = new ShippingAddress();//change it
  emptyPaymentList: boolean = true;
  userPaymentList: UserPayment[] = [];
  payment: Payment = new Payment();
  order= new Order();
  subscription: Subscription;

  constructor(
    private paymentService: PaymentService,
    private router: Router,
    private orderData: OrderDataService) { }

  ngOnInit() {
    this.subscription = this.orderData.orderChanged
      .subscribe(
        (order) => {
          this.order = order;
        }
      );
    this.order = this.orderData.getOrder();
    this.getUserPaymentList();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  setBillingAsShipping(checked: boolean){

    if(checked) {
      this.billingAddress.billingAddressName = this.shippingAddress.shippingAddressName;
      this.billingAddress.billingAddressStreet1 = this.shippingAddress.shippingAddressStreet1;
      this.billingAddress.billingAddressStreet2 = this.shippingAddress.shippingAddressStreet2;
      this.billingAddress.billingAddressCity = this.shippingAddress.shippingAddressCity;
      this.billingAddress.billingAddressState = this.shippingAddress.shippingAddressState;
      this.billingAddress.billingAddressCountry = this.shippingAddress.shippingAddressCountry;
      this.billingAddress.billingAddressZipcode = this.shippingAddress.shippingAddressZipcode;
    } else {
      this.billingAddress.billingAddressName = "";
      this.billingAddress.billingAddressStreet1 = "";
      this.billingAddress.billingAddressStreet2 = "";
      this.billingAddress.billingAddressCity = "";
      this.billingAddress.billingAddressState = "";
      this.billingAddress.billingAddressCountry = "";
      this.billingAddress.billingAddressZipcode = "";
    }
  }

  goToReview() {
    this.router.navigate(['/order/review']);
  }

  getUserPaymentList() {
    this.paymentService.getUserPaymentList().subscribe(
      res => {
        this.userPaymentList = res.json();
        this.emptyPaymentList = false;

        if (this.userPaymentList.length) {
          this.emptyPaymentList = false;

          for (let userPayment of this.userPaymentList) {
            if (userPayment.defaultPayment) {
              this.setPaymentMethod(userPayment);
              return;
            }
          }
        }
      },
      error => {
        console.log(error.text());
      }
    );
  }

  setPaymentMethod(userPayment: UserPayment) {
    this.payment.type = userPayment.type;
    this.payment.cardNumber = userPayment.cardNumber;
    this.payment.expiryMonth = userPayment.expiryMonth;
    this.payment.expiryYear = userPayment.expiryYear;
    this.payment.cvc = userPayment.cvc;
    this.payment.holderName = userPayment.holderName;
    this.payment.defaultPayment = userPayment.defaultPayment;
    this.billingAddress.billingAddressName = userPayment.userBilling.userBillingName;
    this.billingAddress.billingAddressStreet1 = userPayment.userBilling.userBillingStreet1;
    this.billingAddress.billingAddressStreet2 = userPayment.userBilling.userBillingStreet2;
    this.billingAddress.billingAddressCity = userPayment.userBilling.userBillingCity;
    this.billingAddress.billingAddressState = userPayment.userBilling.userBillingState;
    this.billingAddress.billingAddressCountry = userPayment.userBilling.userBillingCountry;
    this.billingAddress.billingAddressZipcode = userPayment.userBilling.userBillingZipcode;

    this.order.payment = this.payment;
    this.order.billingAddress = this.billingAddress;
    this.orderData.setOrder(this.order);
  }

}
