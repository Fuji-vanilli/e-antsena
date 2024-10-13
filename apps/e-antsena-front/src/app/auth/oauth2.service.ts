import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { CreateQueryResult, injectQuery } from '@tanstack/angular-query-experimental';
import { OidcSecurityService } from 'angular-auth-oidc-client';
import { ConnectedUser } from '../shared/model/user.model';
import { firstValueFrom, Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class Oauth2Service {

  httpClient= inject(HttpClient);

  oidcSecurityService= inject(OidcSecurityService);

  connectedUserQuery: CreateQueryResult<ConnectedUser> | undefined;

  notConnected= 'NOT_CONNECTED';

  constructor() { }

  fetch(): CreateQueryResult<ConnectedUser> {
    return injectQuery(()=> ({
      queryKey: ['connected-user'],
      queryFn: ()=> firstValueFrom(this.fetchUserHttp(false))
    }))
  }

  fetchUserHttp(forceSync: boolean): Observable<ConnectedUser> {
    const params= new HttpParams().set('forceSync', forceSync);

    return this.httpClient.get<ConnectedUser>(`${environment.apiUrl}/users/authenticateds`, { params });
  }

  login() {
    this.oidcSecurityService.authorize();
  }

  logout() {
    this.oidcSecurityService.logoff().subscribe();
  }

  initAuthentication() {
    this.oidcSecurityService.checkAuth().subscribe(
      authInfo=> {
        if (authInfo.isAuthenticated) {
          console.log('user connected...');
          
        } else {
          console.log('user not connected!');
          
        }
      }
    )
  }

  hasAnyAuthorities(connectedUser: ConnectedUser, authorities: Array<string> | string): boolean {
    if (!Array.isArray(authorities)) {
      authorities= [authorities];
    }

    if (connectedUser.authorities) {
      return connectedUser.authorities.some((authority: string)=> authorities.includes(authority));
    } else {
      return false;
    }
  }
}
