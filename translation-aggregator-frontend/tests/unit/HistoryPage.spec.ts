import { expect } from "chai";
import { shallowMount } from "@vue/test-utils";
import * as HistoryService from "@/data/history/HistoryService";
import { rightOf } from "@/domain/common/Either";

import sinon from "sinon";
import HistoryPage from "@/view/views/history/HistoryPage.vue";
import flushPromises from "flush-promises";

afterEach(() => {
  sinon.restore();
});

describe("HistoryPage.vue", () => {
  it("shows history", async () => {
    const historyResults = ["a", "b", "c"];

    sinon
      .stub(HistoryService, "getHistory")
      .returns(Promise.resolve(rightOf(historyResults)));

    const wrapper = shallowMount(HistoryPage);

    await flushPromises();

    expect(wrapper.findAll(".container ul li").length).to.eq(
      historyResults.length
    );
  });
});
