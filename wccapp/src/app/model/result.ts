export interface Result<T> {
    data: T;
    total: number;
    code: number;
    msg: string;
}