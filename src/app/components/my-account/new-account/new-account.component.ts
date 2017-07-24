import { Component, OnInit } from '@angular/core';
import {UserService} from '../../../services/user.service';

@Component({
  selector: 'app-new-account',
  templateUrl: './new-account.component.html',
  styleUrls: ['./new-account.component.css']
})
export class NewAccountComponent implements OnInit {

  private emailSent = false;
  private usernameExists = false;
  private emailExists = false;
  private username:string;
  private email:string;

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  onNewAccount() {
    this.userService.newUser(this.username, this.email).subscribe(
      res => {
        this.emailSent = true;
      },
      error => {
        let errorMessage = error.text();
        if(errorMessage==="usernameExists") this.usernameExists = true;
        if(errorMessage==="emailExists") this.emailExists = true;
      }
    );
  }

}
