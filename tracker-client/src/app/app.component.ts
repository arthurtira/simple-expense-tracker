import { Component } from '@angular/core';
import { UserService } from './user/shared/user.service'


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'expenses-tracker-client';

  constructor( public _userService: UserService) {

  }
}
