(function() {
  var callWithJQuery;

  callWithJQuery = function(pivotModule) {
    if (typeof exports === "object" && typeof module === "object") {
      return pivotModule(require("jquery"));
    } else if (typeof define === "function" && define.amd) {
      return define(["jquery"], pivotModule);
    } else {
      return pivotModule(jQuery);
    }
  };

  callWithJQuery(function($) {
    var frFmt, frFmtInt, frFmtPct, nf, tpl;
    nf = $.pivotUtilities.numberFormat;
    tpl = $.pivotUtilities.aggregatorTemplates;
    frFmt = nf({
      thousandsSep: " ",
      decimalSep: ","
    });
    frFmtInt = nf({
      digitsAfterDecimal: 0,
      thousandsSep: " ",
      decimalSep: ","
    });
    frFmtPct = nf({
      digitsAfterDecimal: 1,
      scaler: 100,
      suffix: "%",
      thousandsSep: " ",
      decimalSep: ","
    });
    return $.pivotUtilities.locales.fr = {
      localeStrings: {
        renderError: "Ошибка рендерера.",
        computeError: "Ошибка вичисления.",
        uiRenderError: "Ошибка ссылки.",
        selectAll: "Выбирать все",
        selectNone: "Невыбирать",
        tooMany: "(слышком много)",
        filterResults: "Результат филтра",
        totals: "Всего",
        vs: "ср",
        by: "на",
        apply: "Применять",
        cancel: "Отмена"
      },
      aggregators: {
        "Количество": tpl.count(frFmtInt),
        "Количество одинаковых значеный": tpl.countUnique(frFmtInt),
        "Список одинаковых значеный": tpl.listUnique(", "),
        "Сумма": tpl.sum(frFmt),
        "Сумма целное число": tpl.sum(frFmtInt),
        "Медиан": tpl.average(frFmt),
        "Минимум": tpl.min(frFmt),
        "Максимум": tpl.max(frFmt),
        "Первый": tpl.first(frFmt),
        "Последный": tpl.last(frFmt),
        "Соотношение двух сум": tpl.sumOverSum(frFmt),
        "Верхные 80% ": tpl.sumOverSumBound80(true, frFmt),
        "Нижные 80%": tpl.sumOverSumBound80(false, frFmt),
        "Сумма как фракция всего": tpl.fractionOf(tpl.sum(), "total", frFmtPct),
        "Сумма как фракция строки": tpl.fractionOf(tpl.sum(), "row", frFmtPct),
        "Сумма как фракция колонки": tpl.fractionOf(tpl.sum(), "col", frFmtPct),
        "Количество как фракция всего": tpl.fractionOf(tpl.count(), "total", frFmtPct),
        "Количество как фракция строки": tpl.fractionOf(tpl.count(), "row", frFmtPct),
        "Количество как фракция колонки": tpl.fractionOf(tpl.count(), "col", frFmtPct)
      },
      renderers: {
        "Таблица": $.pivotUtilities.renderers["Table"],
        "Таблица с графиком": $.pivotUtilities.renderers["Table Barchart"],
        "Тепловая таблица": $.pivotUtilities.renderers["Heatmap"],
        "Строковая тепловая таблица": $.pivotUtilities.renderers["Row Heatmap"],
        "Колончатая тепловая таблица": $.pivotUtilities.renderers["Col Heatmap"]
      }
    };
  });

}).call(this);

//# sourceMappingURL=pivot.fr.js.map