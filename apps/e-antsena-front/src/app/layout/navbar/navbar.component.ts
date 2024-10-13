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

  connectedUserQuery= this.oauth2Service.connectedUserQuery;

  login() {
    this.closeDropdownMenu();
    this.oauth2Service.login();
  }

  logout() {
    this.closeDropdownMenu();
    this.oauth2Service.logout();
  }

  isConnected() {
    return (this.connectedUserQuery?.status()=== 'success' && this.connectedUserQuery?.data()?.email!== this.oauth2Service.notConnected);
  }

  closeDropdownMenu() {
    const bodyElement= document.activeElement as HTMLBodyElement;

    if (bodyElement) {
      bodyElement.blur();
    }
  }
}
