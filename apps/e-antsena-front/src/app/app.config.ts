import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { appRoutes } from './app.routes';
import {
  provideHttpClient,
  withInterceptors,
  withXsrfConfiguration,
} from '@angular/common/http';

import { provideAnimations } from '@angular/platform-browser/animations';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(appRoutes),
    provideHttpClient(
      //withInterceptors([authInterceptor]),
      withXsrfConfiguration({cookieName: 'XSRF-TOKEN', headerName: 'X-XSRF-TOKEN'})
    ),

    provideAnimations()
  ]
};