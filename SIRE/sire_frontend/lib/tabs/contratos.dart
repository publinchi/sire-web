// Copyright 2019 The Flutter team. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

import 'package:flutter/material.dart';

import 'package:flutter_gen/gen_l10n/gallery_localizations.dart';
import 'package:sire_frontend/charts/pie_chart.dart';
import 'package:sire_frontend/data.dart';
import 'package:sire_frontend/finance.dart';
import 'package:sire_frontend/tabs/sidebar.dart';

/// A page that shows a summary of accounts.
class ContratosView extends StatelessWidget {
  var contratoDatas;

  ContratosView({this.contratoDatas});

  @override
  Widget build(BuildContext context) {
    final items = contratoDatas;
    final detailItems = DummyDataService.getAccountDetailList(context);
    final balanceTotal = sumContratoDataPrimaryAmount(items);

    return TabWithSidebar(
      restorationId: 'accounts_view',
      mainView: FinancialEntityView(
        heroLabel: GalleryLocalizations.of(context).rallyAccountTotal,
        heroAmount: balanceTotal,
        segments: buildSegmentsFromContratoItems(items),
        wholeAmount: balanceTotal,
        financialEntityCards: buildContratoDataListViews(items, context),
      ),
      sidebarItems: [
        for (UserDetailData item in detailItems)
          SidebarItem(title: item.title, value: item.value)
      ],
    );
  }
}
