import Result from "@/domain/common/Result";
import { axiosInstance } from "@/axios";
import { handle, handleSuccess } from "@/data/common/service-utils";
import UrbanTranslationResult from "@/domain/model/translation/UrbanTranslationResult";
import { rightOf } from "@/domain/common/Either";

export async function translateYandex(query: string): Promise<Result<string>> {
  const baseUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate";

  return handleSuccess(
    axiosInstance.get(baseUrl, {
      params: {
        key:
          "TODO",
        text: query,
        lang: "en-ru"
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

  const url = `https://api.urbandictionary.com/v0/define`;

  const request = axiosInstance.get(url, {
    params: {
      term: query
    }
  });

  return handle(request, response => rightOf(response["list"]));
}
