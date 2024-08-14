import {Component} from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators
} from '@angular/forms';

import {Customer} from '../../../model/account-info';
import {CustomerService} from '../../../service/customer.service';
import {HttpErrorResponse} from '@angular/common/http';
import { NzMessageService } from 'ng-zorro-antd/message';
import { concatMap } from 'rxjs';
import { Router } from '@angular/router';
import { CustomerConstant } from '../../../common/customer.constant';


@Component({
  selector: 'app-account-info',
  templateUrl: './account-info.component.html',
  styleUrl: './account-info.component.less'
})


export class AccountInfoComponent {

  public customer: Customer;
  public customerConstant: CustomerConstant;

  submittable: boolean = false;
  loading: boolean = false;

  loginForm: FormGroup;
  showLoginPanel = true;

  form: FormGroup;
  passwordRequirements: { [key: string]: boolean } = {
    minLength: false,
    uppercase: false,
    lowercase: false,
    number: false,
    match: false
  };
  defaultNationality = 'Malaysian';
  defaultNricType = 'MyKad (Malaysia Citizen)';
  defaultDialingCode = '+60 (Malaysia)';

  public countryData = CustomerConstant.COUNTRY_DATA;
  public icData = CustomerConstant.IC_DATA;
  public salutationData = CustomerConstant.SALUTATION_DATA;
  public dialingCodeData = CustomerConstant.DIALING_CODE_DATA;

  constructor(
    private customerService: CustomerService,
    private message: NzMessageService,
    private fb: FormBuilder,
    private router: Router) {
  }

  ngOnInit() {
    this.createForm();
    this.createFirstLoadMessage();
    this.onNationalityChange();
    this.subscribeToFormChanges();
  }

  createForm(): void {
    this.loginForm = this.fb.group({
      userId: ['', Validators.required],
      password: ['', Validators.required],
    });

    this.form = this.fb.group({
      userId: [null, Validators.required],
      password: [null, Validators.required],
      confirmPassword: [null, [Validators.required, this.confirmPasswordValidator]],
      nationality: [null, Validators.required],
      icType: [null],
      icNo: [null],
      passport: [null],
      issuingCountry: [null],
      expiryDate: [null],
      salutation: [null, Validators.required],
      fullName: [null, Validators.required],
      dialingCode: ['+60 (Malaysia)', Validators.required],
      phoneNo: [null, Validators.required],
      email: [null, [Validators.required, Validators.email]],
    });

    // Set default values
    this.form.patchValue({
      nationality: this.defaultNationality,
      icType: this.defaultNricType,
    });
  }

  onLogin(): void {
    if (this.loginForm.valid) {
      this.customer = this.loginForm.value as Customer;

      this.customerService.login(this.customer).subscribe(
        (response: Customer) => {
          if (response) {
            localStorage.setItem('userId', JSON.stringify(this.customer.userId));
            this.startShowSuccessMessage(this.loginForm, 'login');
          } else {
            this.message.error('Invalid credentials provided... Please verify your UserId and password!');
          }
        },
        (error: HttpErrorResponse) => {
          this.message.error('Some Issue Occurred!');
          console.error('Form is invalid!');
          console.log(error.message);
        }
      );
    } else {
      console.error('Form is invalid!');
      this.message.error('Form is invalid... Please fill in all the required information!');
    }
  }

  onNationalityChange() {
    // this.validateForm.controls.userName.value;
    const selectedNationality = this.form.get('nationality').value;
    if (selectedNationality !== 'Malaysian') {
      this.form.get('icType').clearValidators();
      this.form.get('icNo').clearValidators();
      this.form.get('passport').setValidators(Validators.required);
      this.form.get('issuingCountry').setValidators(Validators.required);
      this.form.get('expiryDate').setValidators(Validators.required);
    } else {
      this.form.get('icType').setValidators(Validators.required);
      this.form.get('icNo').setValidators(Validators.required);
      this.form.get('passport').clearValidators();
      this.form.get('issuingCountry').clearValidators();
      this.form.get('expiryDate').clearValidators();
    }
    this.form.get('icType').updateValueAndValidity();
    this.form.get('icNo').updateValueAndValidity();
    this.form.get('passport').updateValueAndValidity();
    this.form.get('issuingCountry').updateValueAndValidity();
    this.form.get('expiryDate').updateValueAndValidity();
  }

  subscribeToFormChanges() {
      this.form.get('password').valueChanges.subscribe(() => {
          this.checkPassword();
      });

      this.form.get('confirmPassword').valueChanges.subscribe(() => {
          this.checkPassword();
      });
  }

  checkPassword() {
    const passwordControl = this.form.get('password');
    const confirmPasswordControl = this.form.get('confirmPassword');
    const passwordValue = passwordControl.value;
    const confirmPasswordValue = confirmPasswordControl.value;

    const isMinLength = passwordValue.length >= 8;
    const hasUppercase = /[A-Z]/.test(passwordValue);
    const hasLowercase = /[a-z]/.test(passwordValue);
    const hasNumber = /\d/.test(passwordValue);
    const isPasswordMatch = passwordValue === confirmPasswordValue;

    this.passwordRequirements = {
        minLength: isMinLength,
        uppercase: hasUppercase,
        lowercase: hasLowercase,
        number: hasNumber
    };

    // Update submittable flag
    // console.log(`isMinLength is: ${isMinLength}. hasUppercase is: ${hasUppercase}. hasLowercase is: ${hasLowercase}. hasNumber is: ${hasNumber}. isPasswordMatch is: ${isPasswordMatch}`);
    this.submittable = isMinLength && hasUppercase && hasLowercase && hasNumber && isPasswordMatch;

    if (this.submittable) {
       console.log(`Password is valid. Password is: ${passwordValue}. Confirm password is: ${confirmPasswordValue}`);
      // alert(`Password is valid. Password is: ${passwordValue}. Confirm password is: ${confirmPasswordValue}`);
    }
}

  confirmPasswordValidator(control: FormControl) {
    const password = control.parent?.get('password')?.value;
    const confirmPassword = control.value;
    if (password !== confirmPassword) {
      return {confirm: true};
    } else {
      return null;
    }
  }

  public onAddAccountInfo(addForm: any): void {
    document.getElementById('add-account-info').click();
    // this.router.navigate(['account-opening/tax-info']);

    this.customer = addForm.value as Customer;
    this.customer.status = 'pending';
    if(this.submittable) {
      console.log(addForm.value);
      this.customerService.addCustomer(addForm.value).subscribe(
        (response: Customer) => {
          if (response) {
            localStorage.setItem('userId', JSON.stringify(this.customer.userId));
            this.startShowSuccessMessage(addForm, 'register');
          } else {
            this.message.error('Some Issues Occurred');
          }
        },
        (error: HttpErrorResponse) => {
          this.message.error('Some Issue Occurred!');
          console.error('Form is invalid!');
          console.log(error.message);
        }
      );
    } else {
      console.error('Form is invalid!');
      this.message.error('Form is invalid... Please fill in all the required information!');
    }
  }

  createFirstLoadMessage(): void {
    this.message.info('Please fill in your account information');
  }

  startShowSuccessMessage(form: any, action?: string): void {
    this.loading = true;
    this.message
      .loading('Processing...', { nzDuration: 1500 })
      .onClose!.pipe(
        concatMap(() => {
          form.reset();
          const msg = action === 'register' ? 'Account Successfully Registered!' : 'Account Successfully Login!';
          return this.message.success(action, { nzDuration: 1500 }).onClose!;
        }),
      )
      .subscribe(() => {
        console.log('Proceed to next step: postcode section now!');
        this.loading = false;
        this.router.navigate(['account-opening/uk-postcode']);
        // setTimeout(() => {
        //   this.loading = false;
        //   this.router.navigate(['account-opening/tax-info']);
        // }, 200);
      });
  }

  autoTips: Record<string, Record<string, string>> = {
    en: {
      required: 'Input is required'
    },
    default: {
      email: 'The input is not valid email'
    }
  };

}
