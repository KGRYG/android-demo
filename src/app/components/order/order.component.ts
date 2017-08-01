import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {Order} from "../../models/order";
import {Payment} from "../../models/payment";
import {ShoppingCart} from "../../models/shopping-cart";
import {CartService} from "../../services/cart.service";
import {OrderDataService} from "../../services/order-data.service";
import {UserShipping} from "../../models/user-shipping";

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  routeLinks: any[];
  activeLinkIndex: number;
  shoppingCart: ShoppingCart = new ShoppingCart();
  order: Order = new Order();

  constructor(
    private router: Router,
    private cartService: CartService,
    private orderData: OrderDataService,
    ) {
    this.routeLinks = [
      {label: 'Shipping', link: 'shipping'},
      {label: 'Payment', link: 'payment'},
      {label: 'Review', link: 'review'}
    ];
  }

  ngOnInit() {
    this.getCartItemList();
    this.getShoppingCart();

    switch (this.router.url) {
      case '/order/payment': {
        this.activeLinkIndex = 1;
        break;
      }
      case '/order/review': {
        this.activeLinkIndex = 2;
        break;
      }
      default: {
        this.activeLinkIndex = 0;
        break;
      }
    }


  }

  getCartItemList() {
    this.cartService.getCartItemList().subscribe(
      res=>{
        this.order.cartItemList = res.json();
        this.orderData.setOrder(this.order);
      },
      error=>{
        console.log(error.text());
      }
    );
  }

  getShoppingCart() {
    this.cartService.getShoppingCart().subscribe(
      res=>{
        this.shoppingCart = res.json();
        this.order.shoppingCart = this.shoppingCart;
        this.orderData.setOrder(this.order);
      },
      error=>{
        console.log(error.text());
      }
    );
  }


}
