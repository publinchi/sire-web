// Copyright 2019 The Flutter team. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'dart:convert';
import 'dart:math' as math;

import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';

import 'package:animations/animations.dart';
import 'package:sire_frontend/data/options.dart';
import 'package:flutter_gen/gen_l10n/gallery_localizations.dart';
import 'package:sire_frontend/layout/adaptive.dart';
import 'package:sire_frontend/layout/text_scale.dart';
import 'package:sire_frontend/charts/line_chart.dart';
import 'package:sire_frontend/charts/pie_chart.dart';
import 'package:sire_frontend/charts/vertical_fraction_bar.dart';
import 'package:sire_frontend/colors.dart';
import 'package:sire_frontend/data.dart';
import 'package:sire_frontend/formatters.dart';

import 'package:http/http.dart' as http;

class FinancialEntityView extends StatelessWidget {
  const FinancialEntityView({
    this.heroLabel,
    this.heroAmount,
    this.wholeAmount,
    this.segments,
    this.financialEntityCards,
  }) : assert(segments.length == financialEntityCards.length);

  /// The amounts to assign each item.
  final List<RallyPieChartSegment> segments;
  final String heroLabel;
  final double heroAmount;
  final double wholeAmount;
  final List<FinancialEntityCategoryView> financialEntityCards;

  @override
  Widget build(BuildContext context) {
    final maxWidth = pieChartMaxSize + (cappedTextScale(context) - 1.0) * 100.0;
    return LayoutBuilder(builder: (context, constraints) {
      return Column(
        children: [
          ConstrainedBox(
            constraints: BoxConstraints(
              // We decrease the max height to ensure the [RallyPieChart] does
              // not take up the full height when it is smaller than
              // [kPieChartMaxSize].
              maxHeight: math.min(
                constraints.biggest.shortestSide * 0.9,
                maxWidth,
              ),
            ),
            child: RallyPieChart(
              heroLabel: heroLabel,
              heroAmount: heroAmount,
              wholeAmount: wholeAmount,
              segments: segments,
            ),
          ),
          const SizedBox(height: 24),
          Container(
            height: 1,
            constraints: BoxConstraints(maxWidth: maxWidth),
            color: RallyColors.inputBackground,
          ),
          Container(
            constraints: BoxConstraints(maxWidth: maxWidth),
            color: RallyColors.cardBackground,
            child: Column(
              children: financialEntityCards,
            ),
          ),
        ],
      );
    });
  }
}

class FinancialCuotaView extends StatelessWidget {
  const FinancialCuotaView({
    this.heroLabel,
    this.heroAmount,
    this.wholeAmount,
    this.segments,
    this.financialEntityCards,
  }) : assert(segments.length == financialEntityCards.length);

  /// The amounts to assign each item.
  final List<RallyPieChartSegment> segments;
  final String heroLabel;
  final double heroAmount;
  final double wholeAmount;
  final List<FinancialEntityCuotaView> financialEntityCards;

  @override
  Widget build(BuildContext context) {
    final maxWidth = pieChartMaxSize + (cappedTextScale(context) - 1.0) * 100.0;
    return LayoutBuilder(builder: (context, constraints) {
      return Column(
        children: [
          ConstrainedBox(
            constraints: BoxConstraints(
              // We decrease the max height to ensure the [RallyPieChart] does
              // not take up the full height when it is smaller than
              // [kPieChartMaxSize].
              maxHeight: math.min(
                constraints.biggest.shortestSide * 0.9,
                maxWidth,
              ),
            ),
            child: RallyPieChart(
              heroLabel: heroLabel,
              heroAmount: heroAmount,
              wholeAmount: wholeAmount,
              segments: segments,
            ),
          ),
          const SizedBox(height: 24),
          Container(
            height: 1,
            constraints: BoxConstraints(maxWidth: maxWidth),
            color: RallyColors.inputBackground,
          ),
          Container(
            constraints: BoxConstraints(maxWidth: maxWidth),
            color: RallyColors.cardBackground,
            child: Column(
              children: financialEntityCards,
            ),
          ),
        ],
      );
    });
  }
}

/// A reusable widget to show balance information of a single entity as a card.
class FinancialEntityCategoryView extends StatelessWidget {
  const FinancialEntityCategoryView({
    @required this.indicatorColor,
    @required this.indicatorFraction,
    @required this.title,
    @required this.subtitle,
    @required this.semanticsLabel,
    @required this.amount,
    @required this.suffix,
  });

  final Color indicatorColor;
  final double indicatorFraction;
  final String title;
  final String subtitle;
  final String semanticsLabel;
  final String amount;
  final Widget suffix;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    return Semantics.fromProperties(
      properties: SemanticsProperties(
        button: true,
        enabled: true,
        label: semanticsLabel,
      ),
      excludeSemantics: true,
      // TODO(shihaohong): State restoration of
      // FinancialEntityCategoryDetailsPage on mobile is blocked because
      // OpenContainer does not support restorablePush.
      // See https://github.com/flutter/flutter/issues/69924.
      child: OpenContainer(
        transitionDuration: const Duration(milliseconds: 350),
        transitionType: ContainerTransitionType.fade,
        openBuilder: (context, openContainer) =>
            FinancialEntityCategoryDetailsPage(),
        openColor: RallyColors.primaryBackground,
        closedColor: RallyColors.primaryBackground,
        closedElevation: 0,
        closedBuilder: (context, openContainer) {
          return TextButton(
            style: TextButton.styleFrom(primary: Colors.black),
            onPressed: openContainer,
            child: Column(
              children: [
                Container(
                  padding:
                      const EdgeInsets.symmetric(vertical: 16, horizontal: 8),
                  child: Row(
                    children: [
                      Container(
                        alignment: Alignment.center,
                        height: 32 + 60 * (cappedTextScale(context) - 1),
                        padding: const EdgeInsets.symmetric(horizontal: 12),
                        child: VerticalFractionBar(
                          color: indicatorColor,
                          fraction: indicatorFraction,
                        ),
                      ),
                      Expanded(
                        child: Wrap(
                          alignment: WrapAlignment.spaceBetween,
                          crossAxisAlignment: WrapCrossAlignment.center,
                          children: [
                            Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  title,
                                  style: textTheme.bodyText2
                                      .copyWith(fontSize: 16),
                                ),
                                Text(
                                  subtitle,
                                  style: textTheme.bodyText2
                                      .copyWith(color: RallyColors.gray60),
                                ),
                              ],
                            ),
                            Text(
                              amount,
                              style: textTheme.bodyText1.copyWith(
                                fontSize: 20,
                                color: RallyColors.gray,
                              ),
                            ),
                          ],
                        ),
                      ),
                      Container(
                        constraints: const BoxConstraints(minWidth: 32),
                        padding: const EdgeInsetsDirectional.only(start: 12),
                        child: suffix,
                      ),
                    ],
                  ),
                ),
                const Divider(
                  height: 1,
                  indent: 16,
                  endIndent: 16,
                  color: RallyColors.dividerColor,
                ),
              ],
            ),
          );
        },
      ),
    );
  }
}

class FinancialEntityCuotaView extends StatelessWidget {
  const FinancialEntityCuotaView({
    @required this.indicatorColor,
    @required this.indicatorFraction,
    @required this.title,
    @required this.subtitle,
    @required this.semanticsLabel,
    @required this.amount,
    @required this.suffix,
  });

  final Color indicatorColor;
  final double indicatorFraction;
  final String title;
  final String subtitle;
  final String semanticsLabel;
  final String amount;
  final Widget suffix;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    return Semantics.fromProperties(
      properties: SemanticsProperties(
        button: true,
        enabled: true,
        label: semanticsLabel,
      ),
      excludeSemantics: true,
      // TODO(shihaohong): State restoration of
      // FinancialEntityCategoryDetailsPage on mobile is blocked because
      // OpenContainer does not support restorablePush.
      // See https://github.com/flutter/flutter/issues/69924.
      child: OpenContainer(
        transitionDuration: const Duration(milliseconds: 350),
        transitionType: ContainerTransitionType.fade,
        openBuilder: (context, openContainer) =>
            FinancialEntityCuotaDetailsPage(numContrato: int.parse(title)),
        openColor: RallyColors.primaryBackground,
        closedColor: RallyColors.primaryBackground,
        closedElevation: 0,
        closedBuilder: (context, openContainer) {
          return TextButton(
            style: TextButton.styleFrom(primary: Colors.black),
            onPressed: openContainer,
            child: Column(
              children: [
                Container(
                  padding:
                      const EdgeInsets.symmetric(vertical: 16, horizontal: 8),
                  child: Row(
                    children: [
                      Container(
                        alignment: Alignment.center,
                        height: 32 + 60 * (cappedTextScale(context) - 1),
                        padding: const EdgeInsets.symmetric(horizontal: 12),
                        child: VerticalFractionBar(
                          color: indicatorColor,
                          fraction: indicatorFraction,
                        ),
                      ),
                      Expanded(
                        child: Wrap(
                          alignment: WrapAlignment.spaceBetween,
                          crossAxisAlignment: WrapCrossAlignment.center,
                          children: [
                            Column(
                              mainAxisAlignment: MainAxisAlignment.center,
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  title,
                                  style: textTheme.bodyText2
                                      .copyWith(fontSize: 16),
                                ),
                                Text(
                                  subtitle,
                                  style: textTheme.bodyText2
                                      .copyWith(color: RallyColors.gray60),
                                ),
                              ],
                            ),
                            Text(
                              amount,
                              style: textTheme.bodyText1.copyWith(
                                fontSize: 20,
                                color: RallyColors.gray,
                              ),
                            ),
                          ],
                        ),
                      ),
                      Container(
                        constraints: const BoxConstraints(minWidth: 32),
                        padding: const EdgeInsetsDirectional.only(start: 12),
                        child: suffix,
                      ),
                    ],
                  ),
                ),
                const Divider(
                  height: 1,
                  indent: 16,
                  endIndent: 16,
                  color: RallyColors.dividerColor,
                ),
              ],
            ),
          );
        },
      ),
    );
  }
}

/// Data model for [FinancialEntityCategoryView].
class FinancialEntityCategoryModel {
  const FinancialEntityCategoryModel(
    this.indicatorColor,
    this.indicatorFraction,
    this.title,
    this.subtitle,
    this.usdAmount,
    this.suffix,
  );

  final Color indicatorColor;
  final double indicatorFraction;
  final String title;
  final String subtitle;
  final double usdAmount;
  final Widget suffix;
}

FinancialEntityCategoryView buildFinancialEntityFromAccountData(
  AccountData model,
  int accountDataIndex,
  BuildContext context,
) {
  final amount = usdWithSignFormat(context).format(model.primaryAmount);
  final shortAccountNumber = model.accountNumber.substring(6);
  return FinancialEntityCategoryView(
    suffix: const Icon(Icons.chevron_right, color: Colors.grey),
    title: model.name,
    subtitle: '• • • • • • $shortAccountNumber',
    semanticsLabel: GalleryLocalizations.of(context).rallyAccountAmount(
      model.name,
      shortAccountNumber,
      amount,
    ),
    indicatorColor: RallyColors.accountColor(accountDataIndex),
    indicatorFraction: 1,
    amount: amount,
  );
}

FinancialEntityCategoryView buildFinancialEntityFromBillData(
  BillData model,
  int billDataIndex,
  BuildContext context,
) {
  final amount = usdWithSignFormat(context).format(model.primaryAmount);
  return FinancialEntityCategoryView(
    suffix: const Icon(Icons.chevron_right, color: Colors.grey),
    title: model.name,
    subtitle: model.dueDate,
    semanticsLabel: GalleryLocalizations.of(context).rallyBillAmount(
      model.name,
      model.dueDate,
      amount,
    ),
    indicatorColor: RallyColors.billColor(billDataIndex),
    indicatorFraction: 1,
    amount: amount,
  );
}

FinancialEntityCategoryView buildFinancialEntityFromBudgetData(
  BudgetData model,
  int budgetDataIndex,
  BuildContext context,
) {
  final amountUsed = usdWithSignFormat(context).format(model.amountUsed);
  final primaryAmount = usdWithSignFormat(context).format(model.primaryAmount);
  final amount =
      usdWithSignFormat(context).format(model.primaryAmount - model.amountUsed);

  return FinancialEntityCategoryView(
    suffix: Text(
      GalleryLocalizations.of(context).rallyFinanceLeft,
      style: Theme.of(context)
          .textTheme
          .bodyText2
          .copyWith(color: RallyColors.gray60, fontSize: 10),
    ),
    title: model.name,
    subtitle: amountUsed + ' / ' + primaryAmount,
    semanticsLabel: GalleryLocalizations.of(context).rallyBudgetAmount(
      model.name,
      model.amountUsed,
      model.primaryAmount,
      amount,
    ),
    indicatorColor: RallyColors.budgetColor(budgetDataIndex),
    indicatorFraction: model.amountUsed / model.primaryAmount,
    amount: amount,
  );
}

FinancialEntityCuotaView buildFinancialEntityFromContratoData(
  ContratoData model,
  int accountDataIndex,
  BuildContext context,
) {
  final amount = usdWithSignFormat(context).format(model.valorContrato);
  final shortAccountNumber = model.fechaContrato;
  return FinancialEntityCuotaView(
    suffix: const Icon(Icons.chevron_right, color: Colors.grey),
    title: model.numContrato.toString(),
    subtitle: '• • • • • • $shortAccountNumber',
    semanticsLabel: GalleryLocalizations.of(context).rallyAccountAmount(
      model.numContrato,
      shortAccountNumber,
      amount,
    ),
    indicatorColor: RallyColors.accountColor(accountDataIndex),
    indicatorFraction: 1,
    amount: amount,
  );
}

List<FinancialEntityCategoryView> buildAccountDataListViews(
  List<AccountData> items,
  BuildContext context,
) {
  return List<FinancialEntityCategoryView>.generate(
    items.length,
    (i) => buildFinancialEntityFromAccountData(items[i], i, context),
  );
}

List<FinancialEntityCategoryView> buildBillDataListViews(
  List<BillData> items,
  BuildContext context,
) {
  return List<FinancialEntityCategoryView>.generate(
    items.length,
    (i) => buildFinancialEntityFromBillData(items[i], i, context),
  );
}

List<FinancialEntityCategoryView> buildBudgetDataListViews(
  List<BudgetData> items,
  BuildContext context,
) {
  return <FinancialEntityCategoryView>[
    for (int i = 0; i < items.length; i++)
      buildFinancialEntityFromBudgetData(items[i], i, context)
  ];
}

List<FinancialEntityCuotaView> buildContratoDataListViews(
  List<ContratoData> items,
  BuildContext context,
) {
  return List<FinancialEntityCuotaView>.generate(
    items.length,
    (i) => buildFinancialEntityFromContratoData(items[i], i, context),
  );
}

class FinancialEntityCategoryDetailsPage extends StatelessWidget {
  final List<DetailedEventData> items =
      DummyDataService.getDetailedEventItems();

  @override
  Widget build(BuildContext context) {
    final isDesktop = isDisplayDesktop(context);

    return ApplyTextOptions(
      child: Scaffold(
        appBar: AppBar(
          elevation: 0,
          centerTitle: true,
          title: Text(
            GalleryLocalizations.of(context).rallyAccountDataChecking,
            style: Theme.of(context).textTheme.bodyText2.copyWith(fontSize: 18),
          ),
        ),
        body: Column(
          children: [
            SizedBox(
              height: 200,
              width: double.infinity,
              child: RallyLineChart(events: items),
            ),
            Expanded(
              child: Padding(
                padding: isDesktop ? const EdgeInsets.all(40) : EdgeInsets.zero,
                child: ListView(
                  shrinkWrap: true,
                  children: [
                    for (DetailedEventData detailedEventData in items)
                      _DetailedEventCard(
                        title: detailedEventData.title,
                        date: detailedEventData.date,
                        amount: detailedEventData.amount,
                      ),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class FinancialEntityCuotaDetailsPage extends StatelessWidget {
  final List<DetailedEventData> items =
      DummyDataService.getDetailedEventItems();
  final int numContrato;

  FinancialEntityCuotaDetailsPage({this.numContrato});

  Future<List<DetailedCuotaData>> getDetailedCuotaItems(int numContrato) async {
    var domain = 'sire.bmcmotors.com.ec';
    var port = '8000';
    var path = '/cuotas/' + numContrato.toString();
    var uri = Uri.http('$domain:$port', path);
    final response = await http.get(uri);

    if (response.statusCode == 200) {
      // If the server did return a 200 OK response,
      // then parse the JSON.
      Iterable l = json.decode(response.body)['items'];
      List<DetailedCuotaData> detailedCuotaDatas = List<DetailedCuotaData>.from(
          l.map((model) => DetailedCuotaData.fromJson(model)));
      return detailedCuotaDatas;
    } else {
      // If the server did not return a 200 OK response,
      // then throw an exception.
      throw Exception('Failed to load album');
    }
  }

  @override
  Widget build(BuildContext context) {
    final isDesktop = isDisplayDesktop(context);

    return ApplyTextOptions(
        child: FutureBuilder<List<DetailedCuotaData>>(
            future: getDetailedCuotaItems(numContrato),
            builder: (context, snapshot) {
              if (!snapshot.hasData) {
                return const Center(child: CircularProgressIndicator());
              }
              return Scaffold(
                appBar: AppBar(
                  elevation: 0,
                  centerTitle: true,
                  title: Text(
                    GalleryLocalizations.of(context).rallyAccountDataChecking,
                    style: Theme.of(context)
                        .textTheme
                        .bodyText2
                        .copyWith(fontSize: 18),
                  ),
                ),
                body: Column(
                  children: [
                    SizedBox(
                      height: 200,
                      width: double.infinity,
                      child: RallyLineChart(events: items),
                    ),
                    Expanded(
                      child: Padding(
                        padding: isDesktop
                            ? const EdgeInsets.all(40)
                            : EdgeInsets.zero,
                        child: ListView(
                          shrinkWrap: true,
                          children: [
                            for (DetailedCuotaData detailedEventData
                                in snapshot.data)
                              _DetailedCuotaCard(
                                fechaCuota: detailedEventData.fechaCuota,
                                valorCuota: detailedEventData.valorCuota,
                                actualizoPor: detailedEventData.actualizoPor,
                              ),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              );
            }));
  }
}

class _DetailedEventCard extends StatelessWidget {
  const _DetailedEventCard({
    @required this.title,
    @required this.date,
    @required this.amount,
  });

  final String title;
  final DateTime date;
  final double amount;

  @override
  Widget build(BuildContext context) {
    final isDesktop = isDisplayDesktop(context);
    return TextButton(
      style: TextButton.styleFrom(
        primary: Colors.black,
        padding: const EdgeInsets.symmetric(horizontal: 16),
      ),
      onPressed: () {},
      child: Column(
        children: [
          Container(
            padding: const EdgeInsets.symmetric(vertical: 16),
            width: double.infinity,
            child: isDesktop
                ? Row(
                    children: [
                      Expanded(
                        flex: 1,
                        child: _EventTitle(title: title),
                      ),
                      _EventDate(date: date),
                      Expanded(
                        flex: 1,
                        child: Align(
                          alignment: AlignmentDirectional.centerEnd,
                          child: _EventAmount(amount: amount),
                        ),
                      ),
                    ],
                  )
                : Wrap(
                    alignment: WrapAlignment.spaceBetween,
                    children: [
                      Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          _EventTitle(title: title),
                          _EventDate(date: date),
                        ],
                      ),
                      _EventAmount(amount: amount),
                    ],
                  ),
          ),
          SizedBox(
            height: 1,
            child: Container(
              color: RallyColors.dividerColor,
            ),
          ),
        ],
      ),
    );
  }
}

class _DetailedCuotaCard extends StatelessWidget {
  const _DetailedCuotaCard({
    @required this.codEmpresa,
    @required this.codCliente,
    @required this.numContrato,
    @required this.nroCuota,
    @required this.fechaCuota,
    @required this.valorCuota,
    @required this.saldoCuota,
    @required this.tipoCuota,
    @required this.estado,
    @required this.fechaEstado,
    @required this.actualizoPor,
    @required this.fechaActualizacion,
  });

  final String codEmpresa;
  final String codCliente;
  final int numContrato;
  final int nroCuota;
  final String fechaCuota;
  final double valorCuota;
  final double saldoCuota;
  final String tipoCuota;
  final String estado;
  final String fechaEstado;
  final String actualizoPor;
  final String fechaActualizacion;

  @override
  Widget build(BuildContext context) {
    final isDesktop = isDisplayDesktop(context);
    return TextButton(
      style: TextButton.styleFrom(
        primary: Colors.black,
        padding: const EdgeInsets.symmetric(horizontal: 16),
      ),
      onPressed: () {},
      child: Column(
        children: [
          Container(
            padding: const EdgeInsets.symmetric(vertical: 16),
            width: double.infinity,
            child: isDesktop
                ? Row(
                    children: [
                      Expanded(
                        flex: 1,
                        child: _EventTitle(title: actualizoPor),
                      ),
                      _EventFecha(title: fechaCuota),
                      Expanded(
                        flex: 1,
                        child: Align(
                          alignment: AlignmentDirectional.centerEnd,
                          child: _EventAmount(amount: valorCuota),
                        ),
                      ),
                    ],
                  )
                : Wrap(
                    alignment: WrapAlignment.spaceBetween,
                    children: [
                      Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          _EventTitle(title: actualizoPor),
                          _EventFecha(title: fechaCuota),
                        ],
                      ),
                      _EventAmount(amount: valorCuota),
                    ],
                  ),
          ),
          SizedBox(
            height: 1,
            child: Container(
              color: RallyColors.dividerColor,
            ),
          ),
        ],
      ),
    );
  }
}

class _EventAmount extends StatelessWidget {
  const _EventAmount({Key key, @required this.amount}) : super(key: key);

  final double amount;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    return Text(
      usdWithSignFormat(context).format(amount),
      style: textTheme.bodyText1.copyWith(
        fontSize: 20,
        color: RallyColors.gray,
      ),
    );
  }
}

class _EventDate extends StatelessWidget {
  const _EventDate({Key key, @required this.date}) : super(key: key);

  final DateTime date;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    return Text(
      shortDateFormat(context).format(date),
      semanticsLabel: longDateFormat(context).format(date),
      style: textTheme.bodyText2.copyWith(color: RallyColors.gray60),
    );
  }
}

class _EventTitle extends StatelessWidget {
  const _EventTitle({Key key, @required this.title}) : super(key: key);

  final String title;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    return Text(
      title,
      style: textTheme.bodyText2.copyWith(fontSize: 16),
    );
  }
}

class _EventFecha extends StatelessWidget {
  const _EventFecha({Key key, @required this.title}) : super(key: key);

  final String title;

  @override
  Widget build(BuildContext context) {
    final textTheme = Theme.of(context).textTheme;
    return Text(
      title,
      style: textTheme.bodyText2.copyWith(color: RallyColors.gray60),
    );
  }
}
