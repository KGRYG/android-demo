import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {CheckoutService} from "../../../services/checkout.service";
import {Book} from "../../../models/book";
import {OrderDataService} from "../../../services/order-data.service";
import {Subscription} from "rxjs/Subscription";
import {Order} from "../../../models/order";

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent implements OnInit, OnDestroy {

  selectedBook: Book;
  subscription: Subscription;
  order = new Order();

  constructor(
    private router: Router,
    private checkoutService: CheckoutService,
    private orderData: OrderDataService
  ) { }

  ngOnInit() {
    this.subscription = this.orderData.orderChanged
      .subscribe(
        (order) => {
          this.order = order;
        }
      );
    this.order = this.orderData.getOrder();
  }

  onSubmit(){
    this.checkoutService.checkout(
      this.order.shippingAddress,
      this.order.billingAddress,
      this.order.payment,
      this.order.shippingMethod
    ).subscribe(
      res => {
        this.order = res.json();

        this.router.navigate(['/ordersummary']);
      },
      error=>{
        console.log(error.text());
      }
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  onSelect(book: Book) {
    this.selectedBook = book;
    this.router.navigate(['/bookdetail', this.selectedBook.id]);
  }

}
