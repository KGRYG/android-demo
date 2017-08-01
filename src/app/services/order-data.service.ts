import { Injectable } from '@angular/core';
import {Order} from "../models/order";
import {Subject} from "rxjs/Subject";

@Injectable()
export class OrderDataService {

  private order: Order;
  orderChanged = new Subject<Order>();

  constructor() { }

  getOrder() {
    return this.order;
  }

  setOrder(order: Order) {
    this.order = order;
    this.orderChanged.next(this.order);
  }

}
