import 'dart:io';
import 'package:http_server/http_server.dart';

Future main() async {
  var staticFiles = VirtualDirectory('webapp');
  staticFiles.allowDirectoryListing = true;
  staticFiles.directoryHandler = (dir, request)  {
    var indexUri = Uri.file(dir.path).resolve('index.html');
    staticFiles.serveFile(File(indexUri.toFilePath()), request);
  };

  var server = await HttpServer.bind(InternetAddress.loopbackIPv4, 8080);
  await server.forEach(staticFiles.serveRequest);
}