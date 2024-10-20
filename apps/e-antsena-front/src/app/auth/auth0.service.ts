import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Location } from "@angular/common";

@Injectable({
  providedIn: 'root'
})
export class Auth0Service {

  http = inject(HttpClient);

  location = inject(Location);

  notConnected = 'NOT_CONNECTED';

  constructor() { }

  login(): void {
    location.href = `${location.origin}${this.location.prepareExternalUrl('oauth2/authorization/okta')}`;
  }
  
}
