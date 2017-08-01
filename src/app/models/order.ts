import {CartItem} from './cart-item';
import {ShippingAddress} from "./shipping-address";
import {BillingAddress} from "./billing-address";
import {Payment} from "./payment";
import {ShoppingCart} from "./shopping-cart";

export class Order {

	public id: number;
	public orderDate: string;
	public shippingDate: string;
	public shippingMethod = "groundShipping";
	public shippingAddress: ShippingAddress;
	public billingAddress: BillingAddress;
	public payment: Payment;
	public shoppingCart: ShoppingCart;
	public orderStatus: string;
	public orderTotal: number;
	public cartItemList: CartItem[];
}
