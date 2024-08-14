import { SearchModel } from "./search-model";

export class PostcodelatlngSearchModel extends SearchModel {
    id: number;
    postcode: string;
    postcode2: string;
    latitude: number;
    longtitude: number;
}