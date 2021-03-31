
import 'package:flutter/material.dart';
import 'package:sire_frontend/app.dart';
import 'package:sire_frontend/colors.dart';
import 'package:sire_frontend/layout/adaptive.dart';
import 'package:sire_frontend/pages/home.dart';

class OtpPage extends StatefulWidget {
  final int numContrato;

  const OtpPage({
    this.numContrato,
  });

  @override
  _OtpPageState createState() => _OtpPageState(numContrato: numContrato);
}

class _OtpPageState extends State<OtpPage> {
  int numContrato;

  _OtpPageState({
    this.numContrato,
  });

  List<Widget> listViewChildren;

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Scaffold(
        appBar: AppBar(automaticallyImplyLeading: false),
        body: SafeArea(
          child: ListView(
            restorationId: 'otp_list_view',
            shrinkWrap: true,
            padding: const EdgeInsets.symmetric(horizontal: 24,vertical: 200),
            children: listViewChildren = [
              const SizedBox(height: 12),
              TextField(
                textInputAction: TextInputAction.next,
                decoration: InputDecoration(
                  labelText: ('Código'),//GalleryLocalizations.of(context).rallyLoginUsername,
                ),
              ),
              const SizedBox(height: 12),
              TextButton(
                style: TextButton.styleFrom(
                  backgroundColor: RallyColors.buttonColor,
                  primary: Colors.black,
                  padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 24),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                ),
                onPressed: () {
                  Navigator.push(context, new MaterialPageRoute(
                    builder: (BuildContext context) => new HomePage(
                        numContrato: numContrato),
                  ));
                },
                child: Row(
                  children: [
                    const Icon(Icons.send),
                    const SizedBox(width: 80),
                    const Text('Ingresar'),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

}