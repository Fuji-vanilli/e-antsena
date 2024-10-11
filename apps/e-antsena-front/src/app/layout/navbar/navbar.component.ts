import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { Oauth2Service } from '../../auth/oauth2.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, FontAwesomeModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent {
  
  oauth2Service= inject(Oauth2Service);

  login() {
    this.closeDropdownMenu();
    this.oauth2Service.login();
  }

  closeDropdownMenu() {
    let bodyElement= document.activeElement as HTMLBodyElement;

    if (bodyElement) {
      bodyElement.blur();
    }
  }
}
