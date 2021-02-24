import Result from "@/domain/common/Result";
import { axiosInstance } from "@/axios";
import { handleSuccess } from "@/data/common/service-utils";
import UrbanTranslationResult from "@/domain/model/translation/UrbanTranslationResult";
import { rightOf } from "@/domain/common/Either";
import RegularTranslationResult from "@/domain/model/translation/RegularTranslationResult";

export async function translateRegular(
  query: string
): Promise<Result<RegularTranslationResult[]>> {
  return handleSuccess(
    axiosInstance.get("translate/regular", {
      params: {
        word: query
      }
    })
  );
}

export async function translateUrban(
  query: string
): Promise<Result<UrbanTranslationResult[]>> {
  if (query == "") {
    return rightOf([]);
  }

  const request = axiosInstance.get("translate/urban", {
    params: {
      word: query
    }
  });

  return handleSuccess(request);
}
