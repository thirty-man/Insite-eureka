function ParsingDate(date: Date): string {
  const year = date.getFullYear().toString();
  let month = (date.getMonth() + 1).toString();
  let day = date.getDate().toString();

  month = month.padStart(2, "0");
  day = day.padStart(2, "0");

  return `${year}-${month}-${day}`;
}

export default ParsingDate;
