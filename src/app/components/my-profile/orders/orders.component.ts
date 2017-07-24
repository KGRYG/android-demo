import { Component, OnInit } from '@angular/core';
import {Order} from "../../../models/order";
import {OrderService} from "../../../services/order.service";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {

  private order:Order = new Order();
  private displayOrderDetail = false;
  private orderList: Order[] = [];

  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.orderService.getOrderList().subscribe(
      res => {
        this.orderList = res.json();
      },
      error => {
        console.log(error.text());
      }
    );
  }

  onDisplayOrder(order: Order) {
    this.order = order;
    this.displayOrderDetail = true;
  }
}
