package kafka.main;

//@Service
//@RequiredArgsConstructor
//public class ReportService {
//
//    private final KafkaTemplate<String, ReportCreatedEventMessage> kafkaTemplate;
//    private final ReportProcessingExecutor reportProcessingExecutor;
//
//    public void sendReport(ReportCreatedEventMessage reportCreatedEventMessage) {
//        CompletableFuture<SendResult<String, ReportCreatedEventMessage>> future =
//                kafkaTemplate.send("report-topic", reportCreatedEventMessage.getReportId(), reportCreatedEventMessage );
//
//        future.whenComplete((result, ex) -> {
//            if (ex != null) {
//                throw new RuntimeException(ex);
//            } else {
//                reportProcessingExecutor.processBigHeavyTask(reportCreatedEventMessage.getReportId());
//            }
//        });
//    }
//}
