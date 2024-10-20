import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FaIconComponent } from '@fortawesome/angular-fontawesome';
import { ClickOutside } from 'ngxtension/click-outside';
import { Auth0Service } from '../../auth/auth0.service';


@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, FaIconComponent, ClickOutside],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent implements OnInit {

  authService= inject(Auth0Service);

  login(): void {
    this.closeDropDownMenu();
    this.authService.login();
  }

  logout(): void {
    this.closeDropDownMenu();
  }

  isConnected(): boolean {
    return false;
  }

  closeDropDownMenu() {
    const bodyElement = document.activeElement as HTMLBodyElement;
    if (bodyElement) {
      bodyElement.blur();
    }
  }

  closeMenu(menu: HTMLDetailsElement) {
    menu.removeAttribute('open');
  }

  ngOnInit(): void {
    
  }
}