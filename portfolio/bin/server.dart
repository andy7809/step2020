// A simple server in dart. Serves all files in the webapp directory to a
// specified port.
import 'dart:io';
import 'package:http_server/http_server.dart';

// The server takes two command line args:
// 1. A string representing a webapp folder to be served
// 2. An integer of the port that the server should serve to
Future main(List<String> args) async {
  // Starts a virtual directory in the webapp folder
  var staticFiles = VirtualDirectory(args[0]);
  staticFiles.allowDirectoryListing = true;
  // Overrides function that displays the listing of files with a function that
  // serves all webapp files instead.
  staticFiles.directoryHandler = (dir, request)  {
    var indexUri = Uri.file(dir.path).resolve('index.html');
    staticFiles.serveFile(File(indexUri.toFilePath()), request);
  };

  var server = await HttpServer.bind(InternetAddress.loopbackIPv4, int.parse(args[1]));
  print("[INFO] Serving " + args[0] + " at port " + args[1]);
  await server.forEach(staticFiles.serveRequest);
}