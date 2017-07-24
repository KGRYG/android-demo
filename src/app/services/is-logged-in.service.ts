import { Injectable } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from './auth.service';

@Injectable()
export class IsLoggedInService {

  constructor(private router: Router,
              private authService: AuthService) { }

  resolve(): void {
    if (this.authService.isAuthenticated()) this.router.navigate(['/home']);
  }

}
