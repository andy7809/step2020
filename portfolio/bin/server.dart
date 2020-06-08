// A simple server in dart. Serves all files in the webapp directory to a
// specified port.
import 'dart:io';
import 'package:http_server/http_server.dart';

Future main() async {
  // Starts a virtual directory in the webapp folder
  var staticFiles = VirtualDirectory('webapp');
  staticFiles.allowDirectoryListing = true;
  // Overrides function that displays the listing of files with a function that
  // serves all webapp files instead.
  staticFiles.directoryHandler = (dir, request)  {
    var indexUri = Uri.file(dir.path).resolve('index.html');
    staticFiles.serveFile(File(indexUri.toFilePath()), request);
  };

  var server = await HttpServer.bind(InternetAddress.loopbackIPv4, 8080);
  await server.forEach(staticFiles.serveRequest);
  
}