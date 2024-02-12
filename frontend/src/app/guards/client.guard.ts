import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthenticateService} from "../services/authenticate.service";

export const clientGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const userRole = inject(AuthenticateService).getRole();
  const hasRole = (userRole === "CLIENT");
  return hasRole || router.navigate(['welcome']);
};
