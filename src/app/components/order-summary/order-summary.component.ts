import { Component, OnInit } from '@angular/core';
import { Order } from '../../models/order';
import {OrderDataService} from "../../services/order-data.service";


@Component({
  selector: 'app-order-summary',
  templateUrl: './order-summary.component.html',
  styleUrls: ['./order-summary.component.css']
})
export class OrderSummaryComponent implements OnInit {
	order = new Order();
	estimatedDeliveryDate: string;

  constructor(
  	private orderData: OrderDataService
  	) { }

  ngOnInit() {
    this.order = this.orderData.getOrder();

    let deliveryDate = new Date();

    if(this.order.shippingMethod=="groundShipping") {
      deliveryDate.setDate(deliveryDate.getDate()+5);
    } else {
      deliveryDate.setDate(deliveryDate.getDate()+3);
    }

    let days=["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    this.estimatedDeliveryDate = days[deliveryDate.getDay()]+', '+deliveryDate.getFullYear()+'/'+deliveryDate.getMonth()+'/'+deliveryDate.getDate();

  }

}
