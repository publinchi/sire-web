import 'dart:convert';
import 'dart:io';

import 'package:camera/camera.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:sire_frontend/colors.dart';
import 'package:sire_frontend/data/options.dart';
import 'package:sire_frontend/layout/adaptive.dart';
import 'package:sire_frontend/layout/text_scale.dart';

import 'package:http/http.dart' as http;

import 'package:currency_text_input_formatter/currency_text_input_formatter.dart';
import 'package:datetime_picker_formfield/datetime_picker_formfield.dart';

class TakePictureScreen extends StatefulWidget {
  final CameraDescription camera;

  const TakePictureScreen({
    Key key,
    @required this.camera,
  }) : super(key: key);

  @override
  TakePictureScreenState createState() => TakePictureScreenState();
}

class TakePictureScreenState extends State<TakePictureScreen> with RestorationMixin {
  CameraController _controller;
  Future<void> _initializeControllerFuture;
  final RestorableTextEditingController _fechaCuotaController =
  RestorableTextEditingController();
  final RestorableTextEditingController _valorCuotaController =
  RestorableTextEditingController();

  @override
  String get restorationId => 'camara_page';

  @override
  void restoreState(RestorationBucket oldBucket, bool initialRestore) {
    registerForRestoration(_fechaCuotaController, restorationId);
    registerForRestoration(_valorCuotaController, restorationId);
  }

  @override
  void initState() {
    super.initState();
    _controller = CameraController(
      widget.camera,
      ResolutionPreset.medium,
    );

    _initializeControllerFuture = _controller.initialize();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        elevation: 0,
        centerTitle: true,
        title: Text(
          "Foto Recibo", //TODO
          style: Theme.of(context)
              .textTheme
              .bodyText2
              .copyWith(fontSize: 18),
        ),
      ),
      body: FutureBuilder<void>(
          future: _initializeControllerFuture,
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.done) {
              return CameraPreview(_controller);
            } else {
              return Center(child: CircularProgressIndicator());
            }
          }),
      floatingActionButton: FloatingActionButton(
        child: Icon(Icons.camera_alt),
        onPressed: () async {
          try {
            await _initializeControllerFuture;
            final image = await _controller.takePicture();
            Navigator.push(
              context,
              MaterialPageRoute(
                  builder: (context) => DisplayPictureScreen(
                    imagePath: image?.path,
                    fechaCuotaController: _fechaCuotaController.value,
                    valorCuotaController: _valorCuotaController.value,
                  )
              ),
            );
          } catch (e) {
            print(e);
          }
        },
      ),
    );
  }
}

class DisplayPictureScreen extends StatelessWidget {
  final String imagePath;
  final TextEditingController fechaCuotaController;
  final TextEditingController valorCuotaController;

  const DisplayPictureScreen({
    Key key,
    @required this.imagePath,
    @required this.fechaCuotaController,
    @required this.valorCuotaController,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ApplyTextOptions(
      child: Scaffold(
        appBar: AppBar(
          elevation: 0,
          centerTitle: true,
          title: Text(
            "Info Recibo", //TODO
            style: Theme.of(context)
                .textTheme
                .bodyText2
                .copyWith(fontSize: 18),
          ),
        ),
        body: _MainView(
          imageController: Image.file(File(imagePath)),
          fechaCuotaController: fechaCuotaController,
          valorCuotaController: valorCuotaController,
        ),
      ),
    );
  }
}

class _MainView extends StatelessWidget {
  final Image imageController;
  final TextEditingController fechaCuotaController;
  final TextEditingController valorCuotaController;

  const _MainView({
    Key key,
    this.imageController,
    this.fechaCuotaController,
    this.valorCuotaController,
  }) : super(key: key);

  Future<void> _send(BuildContext context) async {
    print('fechaCuota -> ' + fechaCuotaController.text);
    print('valorCuota -> ' + valorCuotaController.value.text);

    var domain = 'sire.bmcmotors.com.ec';
    var port = '8000';
    var path = '/cuota/';
    var uri = Uri.http('$domain:$port', path);
    var body = json.encode(
        {
          'fecha_cuota': fechaCuotaController.text,
          'valor_cuota': valorCuotaController.text
        }
    );
    var headers = <String,String>{
      'Content-type' : 'application/json',
      'Accept': 'application/json'
    };
    var response = await http.post(uri, body: body, headers: headers);
    if(response.statusCode == 200) {
      int codCliente = int.parse(json.decode(response.body)['cod_cliente']);
      //Navigator.push(context, new MaterialPageRoute(
      //  builder: (BuildContext context) => new HomePage(
      //      codCte: clienodCliente),
      //)
      // );
    } else {
      fechaCuotaController.clear();
      valorCuotaController.clear();
    }
  }

  @override
  Widget build(BuildContext context) {
    final isDesktop = isDisplayDesktop(context);
    List<Widget> listViewChildren;

    if (isDesktop) {
      final desktopMaxWidth = 400.0 + 100.0 * (cappedTextScale(context) - 1);
      listViewChildren = [
        imageController,
        const SizedBox(height: 12),
        _FechaCuotaInput(
          maxWidth: desktopMaxWidth,
          fechaCuotaController: fechaCuotaController,
        ),
        const SizedBox(height: 12),
        _ValorCuotaInput(
          maxWidth: desktopMaxWidth,
          valorCuotaController: valorCuotaController,
        ),
        _SendButton(
          maxWidth: desktopMaxWidth,
          onTap: () {
            _send(context);
          },
        ),
      ];
    } else {
      listViewChildren = [
        //const SizedBox(height: 5),
        imageController,
        const SizedBox(height: 10),
        _FechaCuotaInput(
          fechaCuotaController: fechaCuotaController,
        ),
        const SizedBox(height: 10),
        _ValorCuotaInput(
          valorCuotaController: valorCuotaController,
        ),
        const SizedBox(height: 12),
        _SendButton(
          onTap: () {
            _send(context);
          },
        ),
      ];
    }

    return Column(
      children: [
        Expanded(
          child: Align(
            alignment: isDesktop ? Alignment.center : Alignment.topCenter,
            child: ListView(
              restorationId: 'login_list_view',
              shrinkWrap: true,
              padding: const EdgeInsets.symmetric(horizontal: 24),
              children: listViewChildren,
            ),
          ),
        ),
      ],
    );
  }
}

class _FechaCuotaInput extends StatelessWidget {

  _FechaCuotaInput({
    Key key,
    this.maxWidth,
    this.fechaCuotaController,
  }) : super(key: key);

  final double maxWidth;
  final TextEditingController fechaCuotaController;
  final format = DateFormat("yyyy-MM-dd");

  @override
  Widget build(BuildContext context) {
    return Align(
      alignment: Alignment.center,
      child: Container(
        constraints: BoxConstraints(maxWidth: maxWidth ?? double.infinity),
        child: Column(children: <Widget>[
          DateTimeField(
            textInputAction: TextInputAction.next,
            controller: fechaCuotaController,
            decoration: InputDecoration(
              labelText: 'Fecha Recibo',//GalleryLocalizations.of(context).rallyLoginUsername,
            ),
            format: format,
            onShowPicker: (context, currentValue) {
              return showDatePicker(context: context, initialDate: currentValue ?? DateTime.now(), firstDate: DateTime(1900), lastDate: DateTime(2100));
            }
          ),
        ],
        ),
      ),
    );
  }
}

class _ValorCuotaInput extends StatelessWidget {

  const _ValorCuotaInput({
    Key key,
    this.maxWidth,
    this.valorCuotaController,
  }) : super(key: key);

  final double maxWidth;
  final TextEditingController valorCuotaController;

  @override
  Widget build(BuildContext context) {
    return Align(
      alignment: Alignment.center,
      child: Container(
        constraints: BoxConstraints(maxWidth: maxWidth ?? double.infinity),
        child: TextField(
          inputFormatters: [CurrencyTextInputFormatter()],
          keyboardType: TextInputType.number,
          controller: valorCuotaController,
          decoration: InputDecoration(
            labelText: 'Valor Recibo',//GalleryLocalizations.of(context).rallyLoginPassword,
          ),
          obscureText: false,
        ),
      ),
    );
  }
}

class _SendButton extends StatelessWidget {

  const _SendButton({
    Key key,
    @required this.onTap,
    this.maxWidth,
  }) : super(key: key);

  final double maxWidth;
  final VoidCallback onTap;

  @override
  Widget build(BuildContext context) {
    return Align(
      alignment: Alignment.center,
      child: Container(
        constraints: BoxConstraints(maxWidth: maxWidth ?? double.infinity),
        //padding: const EdgeInsets.symmetric(vertical: 24),
        child: Row(
          children: [
            //const Icon(Icons.check_circle_outline,
            //    color: RallyColors.buttonColor),
            //const SizedBox(width: 12),
            //Text(GalleryLocalizations.of(context).rallyLoginRememberMe),
            const Expanded(child: SizedBox.shrink()),
            _FilledButton(
              text: 'Enviar',//GalleryLocalizations.of(context).rallyLoginButtonLogin,
              onTap: onTap,
            ),
          ],
        ),
      ),
    );
  }
}

class _FilledButton extends StatelessWidget {

  const _FilledButton({
    Key key,
    @required this.text,
    @required this.onTap
  })
      : super(key: key);

  final String text;
  final VoidCallback onTap;

  @override
  Widget build(BuildContext context) {
    return TextButton(
      style: TextButton.styleFrom(
        backgroundColor: RallyColors.buttonColor,
        primary: Colors.black,
        padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 24),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(12),
        ),
      ),
      onPressed: onTap,
      child: Row(
        children: [
          const Icon(Icons.send),
          const SizedBox(width: 6),
          Text(text),
        ],
      ),
    );
  }
}

