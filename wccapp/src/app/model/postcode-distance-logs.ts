import { Customer } from "./account-info";
import { Postcodelatlng } from "./postcodelatlng";

export class PostcodeDistanceLogs {
    firstLocation: Postcodelatlng;
    secondLocation: Postcodelatlng;
    customer: Customer;
    distance: string;
    updateDate: Date;
}