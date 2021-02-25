import { AxiosError, AxiosResponse } from "axios";
import Result from "@/domain/common/Result";
import { leftOf, rightOf } from "@/domain/common/Either";

export async function handle<ResultType, ResponseType = object | any[]>(
  call: Promise<AxiosResponse<ResponseType>>,
  onSuccess: (result: ResponseType) => Result<ResultType>,
  onFailure: (error: AxiosError) => Result<ResultType> = error => {
    return leftOf(error.response ? error.response.data : error);
  }
): Promise<Result<ResultType>> {
  try {
    const result = await call;
    return onSuccess(result.data);
  } catch (e) {
    return onFailure(e);
  }
}

// prettier-ignore
export async function handleAsync<ResultType, ResponseType = object | any[]>(
  call: Promise<AxiosResponse<ResponseType>>,
  onSuccess: (result: ResponseType) => Promise<Result<ResultType>>,
  onFailure: (error: AxiosError) => Promise<Result<ResultType>> = async error => {
    return leftOf(error.response ? error.response.data : error);
  }
): Promise<Result<ResultType>> {
  try {
    const result = await call;
    return await onSuccess(result.data);
  } catch (e) {
    return await onFailure(e);
  }
}

export async function handleSuccess<T>(
  call: Promise<AxiosResponse<T>>
): Promise<Result<T>> {
  return handle(call, result => rightOf(result));
}
